package pl.mikigal.restclient.proxy;

import pl.mikigal.restclient.annotations.*;
import pl.mikigal.restclient.data.Argument;
import pl.mikigal.restclient.data.Header;
import pl.mikigal.restclient.enums.ArgumentType;
import pl.mikigal.restclient.utils.RequestUtils;
import pl.mikigal.restclient.validators.ArgumentValidator;
import pl.mikigal.restclient.validators.EndpointValidator;
import pl.mikigal.restclient.validators.RestValidator;
import pl.mikigal.restclient.validators.Validator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class RestInvocationHandler<T> implements InvocationHandler {

    private Class<T> type;
    private Validator restValidator;
    private Header[] headers;

    public RestInvocationHandler(Class<T> type, Header[] headers) {
        this.type = type;
        this.headers = headers;

        this.restValidator = new RestValidator(this.type);
        this.restValidator.validate();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RestApi restApi = type.getAnnotation(RestApi.class);

        Validator methodValidator = new EndpointValidator(method);
        methodValidator.validate();

        Endpoint endpoint = method.getAnnotation(Endpoint.class);

        String auth = null;

        List<Argument> arguments = new ArrayList<>();
        List<Argument> postArguments = new ArrayList<>();

        if(args != null) {
            for(int i = 0; i < args.length; i++) {
                Parameter param = method.getParameters()[i];
                Object value = args[i];

                Validator validator = new ArgumentValidator(type, endpoint, param);
                validator.validate();

                Argument arg = null;
                if(param.isAnnotationPresent(RequestParam.class))
                    arg = new Argument(ArgumentType.REQUEST_PARAM, param.getAnnotation(RequestParam.class).value(), value);
                else if(param.isAnnotationPresent(PathVariable.class))
                    arg = new Argument(ArgumentType.PATH_VARIABLE, param.getAnnotation(PathVariable.class).value(), value);
                else if(param.isAnnotationPresent(RequestBody.class)){
                    arg = new Argument(ArgumentType.REQUEST_BODY, param.getAnnotation(RequestBody.class).value(), value);
                    postArguments.add(arg);
                }
                else
                    auth = value.toString();

                if(arg != null)
                    arguments.add(arg);
            }
        }

        String url = RequestUtils.parse(restApi, endpoint, arguments);
        return RequestUtils.connect(url, restApi, endpoint, postArguments, auth, headers);
    }
}

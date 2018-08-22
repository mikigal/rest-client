package pl.mikigal.restclient.proxy;

import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.annotations.PathVariable;
import pl.mikigal.restclient.annotations.RequestParam;
import pl.mikigal.restclient.annotations.RestApi;
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

        System.out.println("Called REST API: " + restApi.base());
        System.out.println("Called Endpoint: " + endpoint.name());
        System.out.println();
        System.out.println("Arguments: ");

        List<Argument> arguments = new ArrayList<>();
        for(int i = 0; i < args.length; i++) {
            Parameter param = method.getParameters()[i];
            Object value = args[i];

            Validator validator = new ArgumentValidator(type, endpoint, param);
            validator.validate();

            Argument arg;
            if(param.isAnnotationPresent(RequestParam.class))
                arg = new Argument(ArgumentType.REQUEST_PARAM, param.getAnnotation(RequestParam.class).value(), value);
            else
                arg = new Argument(ArgumentType.PATH_VARIABLE, param.getAnnotation(PathVariable.class).value(), value);


            arguments.add(arg);

            System.out.println("Argument type: " + arg.getType());
            System.out.println("Argument name: " + arg.getName());
            System.out.println("Argument value: " + arg.getValue());
            System.out.println();
        }
        System.out.println();
        String url = RequestUtils.parseGet(restApi, endpoint, arguments);
        System.out.println("Parsed URL: " + url);
        System.out.println("Connecting... \n\n");

        return RequestUtils.connect(url, endpoint.method(),  headers);
    }
}

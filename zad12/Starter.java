import java.util.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

public class Starter implements java.util.function.Consumer<String> {
    public void accept(String t){
        try{
            Class c = Class.forName(t);
            Object instance = c.newInstance();

            for(Method m: c.getMethods()){
                MethodToStart ci;
                StringParameter sp;

                Boolean disabled = false;
                Boolean toStart = false;

                int howManyTimes = 1;
                String parameter = "";
                Boolean isPublic = Modifier.isPublic(m.getModifiers());
                
                for(Annotation a: m.getDeclaredAnnotations())
                {
                    if(a instanceof MethodDisabled){
                        disabled = true; 
                    }

                    if(a instanceof MethodToStart){
                        ci = (MethodToStart) a;
                        howManyTimes = ci.value();
                        toStart = true;
                    }

                    if(a instanceof StringParameter){
                        sp = (StringParameter) a;
                        parameter = sp.value();
                    }
                }

                if(!disabled && isPublic && toStart){
                    for(int i=0;i<howManyTimes;i++){
                        if(m.getParameterCount() == 0)
                            m.invoke(instance);

                        if(m.getParameterCount() == 1 && 
                                m.getGenericParameterTypes()[0].getTypeName() == "java.lang.String")
                            m.invoke(instance, parameter);
                    }
                }
            }
        }
        catch(Exception e){}
    }
}

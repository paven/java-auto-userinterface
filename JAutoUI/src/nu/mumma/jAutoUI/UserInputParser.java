/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.mumma.jAutoUI;

import jautoui.*;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.Annotation;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.type.PrimitiveType;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/**
 *
 * @author gustafsp
 */
public class UserInputParser extends JPanel {

    private static LinkedList<Class> Classes = new LinkedList<Class>();


    private UserInputParser parent = null;

    /* Creats a top node */
    public UserInputParser(Class c) {
        this(c, (UserInput) DefoultAnotation.class.getAnnotation(nu.mumma.jAutoUI.UserInput.class), null);
    }

    /*Creats a node of Class content */
    public UserInputParser(Class c, UserInput udParent, nu.mumma.jAutoUI.UserInputParser parent) {
        UserInput ud = (UserInput) c.getAnnotation(nu.mumma.jAutoUI.UserInput.class);

        if (ud != null) {
            ud = udParent;
        }

        if (ud.follow || ud.set) {
            for (Method method : c.getMethods()) {
                if (method.getName().startsWith(ud.prefix)) {
                    JPanel p = new UserInputParser(method, ud, parent);
                    this.add(p);
                }
            }
        }
    }
    /* Creats a node of method content. */

    public UserInputParser(Method method, UserInput udParent, nu.mumma.jAutoUI.UserInputParser parent) {
        UserInput ud = method.getAnnotation(nu.mumma.jAutoUI.UserInput.class);
        if (ud != null) {
            ud = udParent;
        }
        
        for (Class c : method.getParameterTypes()) {
            if (UserInputData.handles(c)) {
                if (ud.set) {
                    UserInputData uid = new UserInputData(c);
                    this.add(uid);
                }
            } else {
                if (ud.follow) {
                    this.loopControle();
                    JPanel p = new UserInputParser(method, ud, parent);
                    this.add(p);
                }
            }
        }
    }

    private boolean loopControle() {
        if (this.getClass() == null ){
           return true;
        }
        else if ( this.parent.getClass() != this.getClass()){
            return loopControle();
        }
        else {
            return false;
        }
    }
}
@UserInput
class DefoultAnotation {
}

class UserInputData extends JPanel {

    private final static LinkedList<Class> inputTypes = new LinkedList<Class>();

    public static LinkedList<Class> getInputTypes() {
        inputTypes.add(String.class);
        return inputTypes;
    }

    public static boolean handles(Class c) {
        return UserInputData.getInputTypes().contains(c);
    }

    public UserInputData(Class c) {
        if (UserInputData.handles(c)) {
            JLabel jl = new JLabel(c.getName());
            JTextArea ja = new JTextArea();
            this.setLayout(new GridLayout(1, 2));
            this.add(jl);
            this.add(ja);
        } else {
            throw new UnsupportedOperationException(c.getName() + " not suported!");
        }
    }

    public String set(String s){
        return s;
    }
}

/* handles the actual input form */
//
//    public void createLables(Class c) {
//        for (Method method : c.getMethods()) {
//
//            System.out.println(method.getName() + "1");
//            if (method.getParameterTypes().length == 1) {
//                System.out.println(method.getName() + "2");
//                if (method.getParameterTypes()[0].isPrimitive()) {
//                    /* convert to Wrapper
//                    System.out.println(method.getName() + "3");
//                    PrimitivSetter s = new PrimitivSetter(method);
//                    this.add(s);
//                    setters.add(s);
//                    setters.size();
//                     */                }
//                if (Trait.class.isAssignableFrom(method.getParameterTypes()[0])) {
//                    System.out.println(method.getName() + "3b");
//                    TraitSetter s = new TraitSetter(method);
//
//
//
//
//                    this.add(s);
//
//
//                    setters.add(s);
//                    //setters.size();
//                    //PrimitiveType prim = new Integer(12);
//                    }
//                if (String.class.isAssignableFrom(method.getParameterTypes()[0])) {
//                    System.out.println(method.getName() + "3b");
//                    TraitSetter s = new TraitSetter(method);
//
//
//
//
//                    this.add(s);
//
//
//                    setters.add(s);
//                    //setters.size();
//                    //PrimitiveType prim = new Integer(12);
//                    }
//            }
//
//*/
//        }
//    }
class PrimitivSetter<T> extends JPanel {

    Class handled = null;
    Method method = null;
    JLabel lable;
    JTextField input = new JTextField();

    public PrimitivSetter(Method m) {
        handled = method.getParameterTypes()[0].getClass();
        method = m;
        lable = new JLabel(method.getName().replaceFirst("set", ""));
        setLayout(new FlowLayout());
        input.setColumns(4);
        add(lable);
        add(input);
    }

    public void set(T value) {
    }
}




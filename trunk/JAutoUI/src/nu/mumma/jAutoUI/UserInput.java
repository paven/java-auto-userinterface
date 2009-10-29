/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nu.mumma.jAutoUI;

/**
 *
 * @author gustafsp
 */
public @interface UserInput {
    boolean follow = false;
    boolean set = true;
    boolean inherit = false;
    String prefix = "set";
}

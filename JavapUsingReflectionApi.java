// Javap Using ReflectionApi
 package com.nit.reflection.api;
import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.lang.reflect.Constructor;
 import java.lang.reflect.Field;
 import java.lang.reflect.Method;
 import java.lang.reflect.Modifier;
 import java.lang.reflect.Parameter;
/**
 * javap command development using Reflection API A small mirror version of
 * javap command for listing class declaration,fields,constructors,methods and
 * inner classes
 *
 * @author Timothy Hawkings
 * @date 28-09-2020
 */
 public class JavapUsingReflectionApi {
 private static Class cls;

 public static void main(String[] args) throws ClassNotFoundException, IOException {
 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 // reading classname from console(Dynamic Nature)
 System.out.print("Enter classname: ");
 String classname = br.readLine();
 // loading class into jvm
 cls = Class.forName(classname);
 System.out.println("Compiled from \"" + classname + "\"");

 // class declaration
 System.out.println(setupClassAndInterfaces());
 // fields
 setupFields();

 // constructors
 setupConstructors();

 // methods
 setupMethods();

 // inner classes
 setupInnerClasses();
 }

 /**
 * this method forms class declaration of the loaded class gathering data of
 * modifiers, class, superclass and implemented interfaces
 *
 * @return
 */
 private static String setupClassAndInterfaces() {
 // collecting all implemented interfaces
 String formInterfaces = "implements ";
 // super class of loaded class
 String superClass = "extends ";
 int interfaceArrLength = 0;

 // checks super class of loaded class
 Class extendsClasse = cls.getSuperclass();
 // does loaded class has superclass or not
 if (extendsClasse != null) {
 superClass = superClass + ("" + extendsClasse).substring(5).trim();
 }

 // lets check whether loaded class implements any interface or not
 @SuppressWarnings("rawtypes")
 Class[] interfacesArr = cls.getInterfaces();
 interfaceArrLength = interfacesArr.length;

 // does loaded class contains interfaces
 if (interfacesArr.length != 0) {
 Class class1 = null;
 // interface names
 for (int i = 0; i < interfacesArr.length; i++) {
 class1 = interfacesArr[i];

 if (i == interfacesArr.length - 1)
 formInterfaces += ("" + class1).substring(10);
 else
 formInterfaces += ("" + class1).substring(10) + ", ";
 }
 }

 // final result
 return Modifier.toString(cls.getModifiers()) + " class " + cls.getName() + " "
 + ((extendsClasse != null) ? superClass : "") + " "
 + (interfaceArrLength != 0 ? formInterfaces : "")
 + " {";
 }

 /**
 * this method used to list all the fields of loaded class setup fields using
 * help of Field class modifiers,data type and name
*/
 private static void setupFields() {
 Field[] fields = cls.getDeclaredFields();
 for (int i = 0; i < fields.length; i++) {
 Field field = fields[i];
 System.out.print(Modifier.toString(field.getModifiers()) + " "
 + field.getType().getName() + " "
 + field.getName() + ";");
 System.out.println();
 }
 }

 /**
 * This method used for listing all constructor available in loaded class
 * modifiers, name, params list and exceptions
 */
 private static void setupConstructors() {
 Constructor[] cons = cls.getDeclaredConstructors();
 Constructor con = null;
 Class[] exceptionClass = null;

 // modifier and name
 for (int i = 0; i < cons.length; i++) {
 con = cons[i];
 System.out.print(Modifier.toString(con.getModifiers()) + " "
 + con.getName() + "(");

 // params list
 Parameter[] params = con.getParameters();
 Parameter parameter = null;
 for (int j = 0; j < params.length; j++) {
 parameter = params[j];

 // just displaying param data type
 // not name...
 // like javap command

 if (j == params.length - 1) {
 System.out.print(("" + parameter).substring(0, (""
 + parameter).length() - 4).trim());
 } else {
 System.out.print(("" + parameter).substring(0, (""
 + parameter).length() - 4) + ",");
 }
 }
 System.out.print(") ");

 // exception list
 exceptionClass = con.getExceptionTypes();
 for (Class clazz : exceptionClass) {
 System.out.println("throws" + ("" + clazz).substring(5) + ";");
 }
 System.out.println();
 }
 }

 /**
 * Using this we can list out all private,public,default,protected declared
 * method of loaded class
 *
 * modifiers, return type, name, params list and exceptions
 */
 private static void setupMethods() {
 Method[] methods = cls.getDeclaredMethods();
 Method method = null;
 Class[] exceptionClass = null;

 // retrieving modifier,return type and name
 for (int i = 0; i < methods.length; i++) {
 method = methods[i];
 System.out.print(Modifier.toString(method.getModifiers()) + " "
 + method.getReturnType() + " "
 + method.getName() + "(");

 // params list
 Parameter[] params = method.getParameters();
 Parameter parameter = null;
 for (int j = 0; j < params.length; j++) {
 parameter = params[j];

 // just displaying param data type
 // not name like javap command
 if (j == params.length - 1) {
 System.out.print(("" + parameter).substring(0, (""
 + parameter).length() - 4).trim());
 } else {
 System.out.print(("" + parameter).substring(0, (""
 + parameter).length() - 4) + ",");
 }
 }
 System.out.print(") ");
 
 // exception list
 exceptionClass = method.getExceptionTypes();
 for (Class clazz : exceptionClass) {
 System.out.println("throws" + ("" + clazz).substring(5) + ";");
 }
 System.out.println();
 }
 }
  
  /**
 * This method is meant for listing all inner classes
*/
 private static void setupInnerClasses() {
 Class[] innerClasses = cls.getNestMembers();
 Class innerClass = null;

 for (int i = 0; i < innerClasses.length; i++) {
 innerClass = innerClasses[i];
 System.out.print(innerClass.getName());
 System.out.println();
 }
  System.out.println("}");
 }
 }

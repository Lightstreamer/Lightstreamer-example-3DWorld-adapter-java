     package com.croftsoft.core.lang;

     /*********************************************************************
     * A semantic interface indicating the class has a static test method.
     *
     * <p align="justify">
     * When a class implements this semantic marker interface, it indicates
     * that it defines a static test method with the following signature:
     * <code>
     * <pre>public static boolean  test ( String [ ]  args )</pre>
     * </code>
     * </p>
     *
     * <p align="justify">
     * This method performs one or more unit self-tests for the class that
     * it is defined in, returning true if the tests were successful and
     * false if otherwise.  It may perform white-box or black-box testing.
     * </p>
     *
     * <p>
     * When provided with a null or empty String array argument, it
     * performs its default test.
     * The default test must not have persistent side-effects such as
     * creating files or modifying static variables nor attempt operations
     * that have nuisance effects.
     * The default test may write to the standard output and the standard
     * error to facilitate debugging.
     * If provided with a non-empty String array argument, it may run
     * tests that have persistent side-effects.
     * </p>
     *
     * <p align="justify">
     * The test method can be called by the class main method, passing the
     * command-line arguments as the test method arguments.  This makes it
     * easy to test the class from the command-line during development.
     * Note that if no command-line arguments are specified, the argument
     * is an empty String array and the default test will be run.
     * <code>
     * <pre>
     * public static void  main ( String [ ]  args )
     * {
     *   System.out.println ( test ( args ) );
     * }
     * </pre>
     * </code>
     * </p>
     *
     * <p align="justify">
     * Automated testing of all the classes in a library can be performed
     * using reflection to test if a class implements the interface
     * Testable.  If the class does, its test method can be called using
     * reflection and the boolean result reported along with anything
     * written to the standard output and standard error.  If a class
     * contains a test method that conforms to the required signature but
     * it does not implement the semantic interface Testable, the test
     * method should not be executed as the similarity may be coincidental.
     * </p>
     *
     * @version
     *   2003-03-12
     * @since
     *   2003-03-12
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Testable { }
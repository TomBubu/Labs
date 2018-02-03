/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

/**
 *
 * @author ee16ttz
 */
public class ClsMathematicalOperation {
    private double Pi = 3.141592653589793;
    
    public ClsMathematicalOperation() {
    }
    
    /*##########################################################################################*/
    /*####################################### Methods ##########################################*/
    /*##########################################################################################*/
    
    public double MultiplyMethod(double Input1, double Input2){
        return (Input1*Input2);
    }

    public double power(double Input1, double Input2) {
        // 5^3 = 5 * 5 * 5 = 125
        //Can I set initial value of Output to 1? Yes.
        double Output = 1;

        if (Input2 >= 1.00) {
            for (int i = 0; i < Input2; i++) {
                Output *= Input1;
                //Output = Output * Input1;
            }
        }
        return Output;
    }

    public double RectangularVolume(double Input1, double Input2, double Input3) {
        return (MultiplyMethod(Input1, Input2) * Input3);
    }

    public double CylinderVolume(double Input1, int Power, double Input2) {
        return (Pi * power(Input1, Power) * Input2);
    }

    public double ConeVolume(double Input1, double Input2) {
        return ((1.0 / 3.0) * CylinderVolume(Input1, 2, Input2));
    }

    public double SquareBasedPyramidVolume(double Input1, double Input2) {
        return (MultiplyMethod(Input1, Input2) * (1.0 / 3.0));
    }

    public double SphereVolume(double Input1, int Power) {
        return (Pi * (4.0 / 3.0) * (power(Input1, Power)));
    }
}

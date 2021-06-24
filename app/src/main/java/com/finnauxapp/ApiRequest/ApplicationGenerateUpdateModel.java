package com.finnauxapp.ApiRequest;

import java.util.ArrayList;

public class ApplicationGenerateUpdateModel {

    public GenerateApplicationRequest getApplication() {
        return Application;
    }

    public void setApplication(GenerateApplicationRequest application) {
        Application = application;
    }

    GenerateApplicationRequest Application;
    public ArrayList<StepEMIModel> getStepIRR() {
        return StepIRR;
    }

    public void setStepIRR(ArrayList<StepEMIModel> stepIRR) {
        StepIRR = stepIRR;
    }

    public ArrayList<StepEMIModel> StepIRR;
}

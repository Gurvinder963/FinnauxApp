package com.finnauxapp.ApiRequest;

public class ApplicationDocSaveRequest {
    public int ApplicationId;
    public int DocId;
    public int LoginUserId;

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int applicationId) {
        ApplicationId = applicationId;
    }

    public int getDocId() {
        return DocId;
    }

    public void setDocId(int docId) {
        DocId = docId;
    }

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public String getDocTitle() {
        return DocTitle;
    }

    public void setDocTitle(String docTitle) {
        DocTitle = docTitle;
    }

    public String getDocFileName() {
        return DocFileName;
    }

    public void setDocFileName(String docFileName) {
        DocFileName = docFileName;
    }

    public String DocTitle;
    public String DocFileName;
}

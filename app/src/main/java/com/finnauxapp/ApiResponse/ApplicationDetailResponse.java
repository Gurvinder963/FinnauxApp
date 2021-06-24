package com.finnauxapp.ApiResponse;

import java.util.List;

public class ApplicationDetailResponse {


    private List<ApplicationItem1> Item1;
    private List<ApplicationItem2> Item2;

    public List<ApplicationItem1> getItem1() {
        return Item1;
    }

    public void setItem1(List<ApplicationItem1> Item1) {
        this.Item1 = Item1;
    }

    public List<ApplicationItem2> getItem2() {
        return Item2;
    }

    public void setItem2(List<ApplicationItem2> Item2) {
        this.Item2 = Item2;
    }


}

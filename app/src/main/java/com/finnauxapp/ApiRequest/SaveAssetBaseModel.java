package com.finnauxapp.ApiRequest;

import java.util.ArrayList;

public class SaveAssetBaseModel {


    public ArrayList<SaveAssetChileModel> getAssetDetails() {
        return AssetDetails;
    }

    public void setAssetDetails(ArrayList<SaveAssetChileModel> assetDetails) {
        AssetDetails = assetDetails;
    }

    public ArrayList<SaveAssetChileModel> AssetDetails;
}

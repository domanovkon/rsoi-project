package com.domanov.gatewayservice.dto;

import java.util.List;

public class ShowMuseumReponse {

    private List<String> showUidList;

    public List<String> getShowUidList() {
        return showUidList;
    }

    public void setShowUidList(List<String> showUidList) {
        this.showUidList = showUidList;
    }
}

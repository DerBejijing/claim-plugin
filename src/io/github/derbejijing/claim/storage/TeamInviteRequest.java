package io.github.derbejijing.claim.storage;

import java.util.Calendar;
import java.util.Date;

public class TeamInviteRequest {

    public String from;
    public String to;
    private Date time_invalid;
    

    public TeamInviteRequest(String from, String to) {
        this.from = from;
        this.to = to;

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, 2);
        this.time_invalid = calendar.getTime();
    }


    public boolean timeout_reached() {
        Date now = new Date();
        if(now.compareTo(this.time_invalid) > 0) return true;
        return false;
    }

}

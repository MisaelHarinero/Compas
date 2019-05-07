package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.MainActivity;
import com.mhdeveloper.compas.model.Ticket;

public class NtNotificationNewTickets implements INt {
    private static MainActivity activity;
    private Ticket ticket;

    public NtNotificationNewTickets(Ticket tk) {
        this.ticket = tk;
        action();

    }

    @Override
    public void action() {
        if (activity != null && this.ticket != null){
            activity.notificationNewTicket(this.ticket);
        }
    }

    public static MainActivity getActivity() {
        return activity;
    }

    public static void setActivity(MainActivity activity) {
        NtNotificationNewTickets.activity = activity;
    }
}

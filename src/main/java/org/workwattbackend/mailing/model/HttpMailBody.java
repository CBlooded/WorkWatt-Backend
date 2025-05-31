package org.workwattbackend.mailing.model;

import org.workwattbackend.user.UserEntity;

public class HttpMailBody {

    //TODO better message
    public static String getActivationMail(UserEntity user, String temporaryPassword, String hostId) {
        return String.format("<h2>Hello %s</h2>" +
                "Your supervisor created account for you.<br/>" +
                "Click on the link below, pass temporary password and set your new personal password to activate the account. <br/>" +
                "Temporary password: %s <br/>" +
                "Activate your account <a href='http://localhost:5173/account/confirm?h=%s'>here</a>. <br/>",
            user.getFirstName(),
            temporaryPassword,
            hostId
        );
    }
}

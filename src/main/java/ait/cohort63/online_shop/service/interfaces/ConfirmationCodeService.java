package ait.cohort63.online_shop.service.interfaces;

import ait.cohort63.online_shop.model.entity.User;

public interface ConfirmationCodeService {

    String generationConfirmationCode(User user);
}

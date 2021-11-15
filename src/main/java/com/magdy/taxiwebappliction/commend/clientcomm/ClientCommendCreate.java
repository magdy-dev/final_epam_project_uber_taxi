package com.magdy.taxiwebappliction.commend.clientcomm;

import com.magdy.taxiwebappliction.commend.Commend;
import com.magdy.taxiwebappliction.commend.Page;
import com.magdy.taxiwebappliction.entity.Client;
import com.magdy.taxiwebappliction.service.ServiceException;
import com.magdy.taxiwebappliction.service.ClientServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class ClientCommendCreate implements Commend {


    private static final Logger logger = Logger.getLogger(ClientCommendRead.class.getName());

    @Override
    public Page execute(HttpServletRequest httpServletRequest) throws ServiceException {
        ClientServiceImpl clientServiceImpl = new ClientServiceImpl();
        Client client = new Client();
        try {

            client.setName("name");
            client.setLastName("lastName");
            client.setEmail("email");
            client.setPassword("password");
            client.setPhoneNumber("phoneNumber");
            client = clientServiceImpl.save(client);
            logger.info("creat" + client);

        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        }
        return new Page("/home.jsp", true, "Success!");
    }
}

package com.magdy.taxiwebappliction.commend.common;

import com.magdy.taxiwebappliction.commend.Command;
import com.magdy.taxiwebappliction.commend.Page;
import com.magdy.taxiwebappliction.entity.Driver;
import com.magdy.taxiwebappliction.service.impl.DriverServiceImpl;
import com.magdy.taxiwebappliction.service.ServiceException;

import javax.servlet.http.HttpServletRequest;


public class AccountCommandRegisterDriver implements Command {

    @Override
    public Page execute(HttpServletRequest httpServletRequest) throws  ServiceException {

        Driver driver = new Driver();
        DriverServiceImpl driverService = new DriverServiceImpl();

        driver.setName(httpServletRequest.getParameter("firstname"));
        driver.setLastName(httpServletRequest.getParameter("lastname"));
        driver.setCarNumber(httpServletRequest.getParameter("carNumber"));
        driver.setPassword(httpServletRequest.getParameter("password"));
        driver.setEmail(httpServletRequest.getParameter("email"));
        driver.setPhoneNumber(httpServletRequest.getParameter("phoneNumber"));

        driver = driverService.save(driver);
        if (driver == null){
            return new Page("/pages/common/registerDriver.jsp", true, "Not Success!");
        }
        return new Page("/pages/common/login.jsp", true, "Success!");
    }
}

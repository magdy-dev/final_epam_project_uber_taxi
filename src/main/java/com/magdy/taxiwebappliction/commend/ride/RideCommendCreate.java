package com.magdy.taxiwebappliction.commend.ride;

import com.magdy.taxiwebappliction.commend.Commend;
import com.magdy.taxiwebappliction.commend.Page;
import com.magdy.taxiwebappliction.commend.clientcomm.ClientCommendRead;
import com.magdy.taxiwebappliction.dao.impl.OrderDaoImpl;
import com.magdy.taxiwebappliction.entity.*;
import com.magdy.taxiwebappliction.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class RideCommendCreate implements Commend {
    private static final Logger logger = Logger.getLogger(ClientCommendRead.class.getName());


    @Override
    public Page execute(HttpServletRequest httpServletRequest) throws ServiceException {
        RideServiceImpl rideService = new RideServiceImpl();
        Ride ride = new Ride();
        AddressServiceImpl addressService = new AddressServiceImpl();
        Address addressFrom = new Address();
        Address addressTo = new Address();
        OrderServiceImpl orderService = new OrderServiceImpl();
        Order order = new Order();
        DriverServiceImpl driverService = new DriverServiceImpl();
        Driver driver = new Driver();
        ClientServiceImpl clientService = new ClientServiceImpl();
        Client client = new Client();

        try {
            addressFrom.setTown("minsk");
            addressFrom.setStreet("riga");
            addressFrom.setBuilding(22);
            addressService.save(addressFrom);

            addressTo.setTown("minsk");
            addressTo.setStreet("nemiga");
            addressTo.setBuilding(33);
            addressService.save(addressTo);

            driver.setName("magdy");
            driver.setLastName("shenoda");
            driver.setEmail("amamama@.gmail.com");
            driver.setPassword("12345");
            driver.setCarNumber("122333bb");
            driver.setPhoneNumber("123344333");
            clientService.save(client);

            client.setName("magdyaa");
            client.setLastName("shenoda");
            client.setEmail("111amamama@.gmail.com");
            client.setPassword("12345");
            client.setPhoneNumber("+122333bb");
            clientService.save(client);

            order.setData("22.22");
            order.setDriver(driver);
            order.setClient(client);
            orderService.save(order);

            ride.setAddressIdFrom(addressFrom);
            ride.setAddressIdTo(addressTo);
            ride.setOrderId(order);
            rideService.save(ride);
            logger.info("create" + ride);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());

        }

        return new Page("/home.jsp", true, "Success!");
    }
}

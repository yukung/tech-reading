package com.customer.web.controller;

import com.customer.biz.domain.Customer;
import com.customer.biz.service.CustomerService;
import com.customer.biz.service.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/customer/{customerId}")
@SessionAttributes("editCustomer")
public class CustomerEditController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/edit", method = GET)
    public String redirectToEntryForm(@PathVariable int customerId, Model model) throws DataNotFoundException {
        Customer customer = customerService.findById(customerId);
        model.addAttribute("editCustomer", customer);
        return "redirect:enter";
    }

    @RequestMapping(value = "/enter", method = GET)
    public String showEntryForm() {
        return "customer/edit/enter";
    }

    @RequestMapping(value = "/enter", params = "_event_proceed", method = POST)
    public String verify(@Valid @ModelAttribute("editCustomer") Customer customer, Errors errors) {
        if (errors.hasErrors()) {
            return "/customer/edit/enter";
        }
        return "redirect:review";
    }

    @RequestMapping(value = "/review", method = GET)
    public String showReview() {
        return "customer/edit/review";
    }

    @RequestMapping(value = "/review", params = "_event_revise", method = POST)
    public String revise() {
        return "redirect:enter";
    }

    @RequestMapping(value = "/review", params = "_event_confirmed", method = POST)
    public String edit(@ModelAttribute("editCustomer") Customer customer,
                       RedirectAttributes redirectAttributes,
                       SessionStatus sessionStatus) throws DataNotFoundException {
        customerService.update(customer);
        redirectAttributes.addFlashAttribute("editCustomer", customer);
        sessionStatus.setComplete();
        return "redirect:/customer";
    }

    @RequestMapping(value = "/edited", method = GET)
    public String showEdited(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "customer/edit/edited";
    }
}

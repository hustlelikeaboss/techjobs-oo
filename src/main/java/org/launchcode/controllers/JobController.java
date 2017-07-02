package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job singleJob = jobData.findById(id);
        model.addAttribute("singleJob", singleJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            model.addAttribute("jobForm", jobForm);
            return "new-job";
        }

        Job newJob = createJobFromForm(jobForm);
        jobData.add( newJob );
        return "redirect:/job?id=" + newJob.getId();
    }

    // method to create a new job
    public Job createJobFromForm(JobForm jobForm) {

        Job newJob = new Job();

        newJob.setName(jobForm.getName());

        Employer e = jobData.getEmployers().findById(jobForm.getEmployerId());
        newJob.setEmployer(e);

        Location l = jobData.getLocations().findById(jobForm.getLocationId());
        newJob.setLocation(l);

        CoreCompetency c = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        newJob.setCoreCompetency(c);

        PositionType p = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        newJob.setPositionType(p);

//        for (Employer employer : jobForm.getEmployers()) {
//            if ( employer.getId() == jobForm.getEmployerId() ) {
//                newJob.setEmployer(employer);
//            }
//        }
//
//        for (Location location : jobForm.getLocations()) {
//            if ( location.getId() == jobForm.getLocationId() ) {
//                newJob.setLocation(location);
//            }
//        }
//
//        for (CoreCompetency coreCompetency : jobForm.getCoreCompetencies()) {
//            if ( coreCompetency.getId() == jobForm.getCoreCompetencyId()) {
//                newJob.setCoreCompetency(coreCompetency);
//            }
//        }
//
//        for (PositionType positionType : jobForm.getPositionTypes()) {
//            if (positionType.getId() == jobForm.getPositionTypeId()) {
//                newJob.setPositionType(positionType);
//            }
//        }

        return newJob;
    }






}

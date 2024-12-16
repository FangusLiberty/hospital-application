package com.example.hospitalapplication.Controllers;

import com.example.hospitalapplication.Entity.Doctor;
import com.example.hospitalapplication.Entity.Specialty;
import com.example.hospitalapplication.Exception.DoctorNotFoundException;
import com.example.hospitalapplication.Exception.SpecialtyNotFoundException;
import com.example.hospitalapplication.Repository.DoctorRepository;
import com.example.hospitalapplication.Service.DoctorService;
import com.example.hospitalapplication.Service.SpecialtyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.Period;

@Controller
@RequestMapping("/registartor")
@RequiredArgsConstructor
public class RegistratorController {

    private final SpecialtyService specialtyService;
    private final DoctorService doctorService;


    @GetMapping("/settings")
    public String settingsOfRegistartor(Model model) {
        model.addAttribute("spec", specialtyService.findAll());
        return "registartor-settings";
    }

    @GetMapping()
    public String registartor() {
        return "registrator";
    }

    @GetMapping("/settings/{specialty_id}/doctors")
    public String doctorsOfSpecialty(Model model, @PathVariable Long specialty_id) throws DoctorNotFoundException {
        model.addAttribute("doctors", doctorService.getAllBySpecialtuId(specialty_id));
        return "registartor-specialty-doctors";
    }

    @GetMapping("/settings/{specialty_id}/edit")
    public String editSpecialtyPage(Model model, @PathVariable Long specialty_id) {
        model.addAttribute("specialty", specialtyService.getSpecialtyById(specialty_id));
        return "edit-specialty";
    }

    @PostMapping("/settings/{specialty_id}/edit")
    public String editSpecialty(
            @RequestParam int doctorCount,
            @RequestParam double salaryRate,
            @RequestParam double totalWages,
            @PathVariable Long specialty_id
           ) throws SpecialtyNotFoundException {
        Specialty specialty = new Specialty();
        specialty.setDoctorCount(doctorCount);
        specialty.setSalaryRate(salaryRate);
        specialty.setTotalWages(totalWages);

        specialtyService.updateSpecialty(specialty, specialty_id);

        return "redirect:/registartor";
    }

    @GetMapping("/settings/specialty-add")
    public String addSpecialtyPage() {
        return "add-specialty";
    }

    @PostMapping("/settings/specialty-add")
    public String addSpecialty(@RequestParam int doctorCount,
                               @RequestParam double salaryRate,
                               @RequestParam double totalWages,
                               @RequestParam String name,
                               @RequestParam boolean isSpecialist
    ) {
        Specialty specialty = new Specialty();
        specialty.setName(name);
        specialty.setTotalWages(totalWages);
        specialty.setSalaryRate(salaryRate);
        specialty.setDoctorCount(doctorCount);
        specialty.setSpecialist(isSpecialist);
        specialtyService.addSpecialty(specialty);
        return "redirect:/registartor";
    }


    @GetMapping("/settings/{user_id}/doctor/{doctor_id}/edit")
    public String editDoctorPage(Model model, @PathVariable Long doctor_id, @PathVariable Long user_id) {
        model.addAttribute("doc", doctorService.getDoctorById(doctor_id));
        model.addAttribute("spec", specialtyService.getSpecialtyById(user_id));
        return "registartor-specialty-doctor-edit";
    }
    @PostMapping("/settings/{spec_id}/doctors/{doctor_id}/edit")
    public String updateDoctor(@PathVariable Long doctor_id, @ModelAttribute Doctor updatedDoctor) {
        try {
            doctorService.updateDoctor(doctor_id, updatedDoctor);
            return "redirect:/registartor";
        } catch (DoctorNotFoundException e) {
            // Handle the exception as needed
            return "error-page";
        }
    }
    @GetMapping("/settings/{spec_is}/doctors/add")
    public String addDoctorPage (Model model, @PathVariable Long spec_is){
        model.addAttribute("spec",specialtyService.getSpecialtyById(spec_is));
        return "add-doctor";
    }
    @PostMapping("/settings/{spec_id}/doctors/add")
    public String addDoctor(@ModelAttribute Doctor doctor, @PathVariable Long spec_id, Model model) throws SpecialtyNotFoundException {
        LocalDate birthDate = doctor.getBirthDate();
        LocalDate admissionDate = doctor.getEmploymentDate();


        int age = Period.between(birthDate, admissionDate).getYears();


        if (age < 20) {
            model.addAttribute("error", "Doctor must be at least 20 years old.");
            return "errorPage";
        }

        doctorService.createDoctor(doctor, spec_id);
        return "redirect:/registartor";
    }
    @PostMapping("/settings/{spec_id}/doctor/{doctor_id}/delete")
    public String deleteDoctor(@PathVariable Long spec_id, @PathVariable Long doctor_id) throws DoctorNotFoundException {
        doctorService.deleteDoctor(doctor_id);
        return "redirect:/registartor/settings/" + spec_id + "/doctors";
    }
    @PostMapping("/settings/{spec_id}/delete")
    public String deleteSpecialty(@PathVariable Long spec_id) throws SpecialtyNotFoundException {
        specialtyService.deleteSpecialty(spec_id);
        return "redirect:/registartor";
    }





}

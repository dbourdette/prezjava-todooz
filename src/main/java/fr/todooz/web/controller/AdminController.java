package fr.todooz.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.todooz.domain.Task;
import fr.todooz.service.TagCloudService;
import fr.todooz.service.TaskService;
import fr.todooz.util.TagCloud;

@Controller
public class AdminController {
    @Inject
    private TaskService taskService;

    @Inject
    private TagCloudService tagCloudService;

    @InitBinder
    public void initBinder(DataBinder binder) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormatter, true));
    }

    @ModelAttribute
    public TagCloud tagCloud() {
        return tagCloudService.buildTagCloud();
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("task", new Task());

        return "edit";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.findById(id));

        return "edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String post(@ModelAttribute @Valid Task task, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit";
        }

        taskService.save(task);

        redirectAttributes.addFlashAttribute("flashMessage", "La sauvegarde a réussi");

        return "redirect:/";
    }

    @RequestMapping("/edit/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.delete(id);

        redirectAttributes.addFlashAttribute("flashMessage", "La suppression a réussi");

        return "redirect:/";
    }
}

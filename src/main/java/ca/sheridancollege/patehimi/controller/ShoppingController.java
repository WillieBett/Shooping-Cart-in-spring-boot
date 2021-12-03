package ca.sheridancollege.patehimi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.patehimi.beans.ShoppingItem;
import ca.sheridancollege.patehimi.database.DatabaseAccess;

@Controller
public class ShoppingController {
	@Autowired
	private DatabaseAccess da;

	@GetMapping("/")
	public String view(Model model) {
		model.addAttribute("my_purchases", da.getAllmy_purchases());
		return "view";
	}

	@GetMapping("/modify/{id}")
	public String editItem(@PathVariable int id, Model model) {
		model.addAttribute("my_purchases", da.selectItem(id));
		return "edit";
	}

	@PostMapping("/doEdit")
	public String doEdit(@ModelAttribute ShoppingItem i) {
		da.modifyItem(i);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteItem(@PathVariable int id) {
		da.deleteItem(id);
		return "redirect:/";
	}

	@GetMapping("/add/{id}")
	public String insertItem(@PathVariable int id, Model model) {
		model.addAttribute("my_purchases", da.selectItem(id));
		return "add";
	}

//	@PostMapping("/additem")
//	public String additem(@ModelAttribute ShoppingItem a) {
//	da.addItem(a);
//	return "redirect:/";
//	}
	@GetMapping("/additem")
	public String additem(@RequestParam String name, @RequestParam String description, @RequestParam Double price,
			@RequestParam String link) {
		ShoppingItem item = new ShoppingItem(0L, name, description, price, link);
		da.addItem(item);
		return "redirect:/";
	}
}
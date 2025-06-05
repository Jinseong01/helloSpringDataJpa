package hw2.controller;

import hw2.entity.Product;
import hw2.service.product.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl service;

    @GetMapping({"", "/"}) // products 또는 /products/ 둘 다 매핑
    public String viewHomePage(Model model, @ModelAttribute("success") String success) {
        // 로그인 성공 메시지
        model.addAttribute("success", success);

        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);

        return "product/index";
    }

    @GetMapping("/new")
    public String showNewProductPage(Model model) {

        Product product = new Product();
        model.addAttribute("product", product);

        return "product/new_product";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductPage(@PathVariable(name = "id") Long id, Model model) {

        Product product = service.get(id);
        model.addAttribute("product", product);

        return "product/edit_product";
    }

    // @ModelAttribute는  Form data (예: name=Laptop&brand=Samsung&madeIn=Korea&price=1000.00)를 Product 객체
    // @RequestBody는 HTTP 요청 본문에 포함된
    //  JSON 데이터(예: {"name": "Laptop", "brand": "Samsung", "madeIn": "Korea", "price": 1000.00})를 Product 객체에 매핑
    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        boolean flag = (product.getId() == null);

        // 유효성 검사
        if (bindingResult.hasErrors()) {
            // id가 null이면 등록, 아니면 수정
            return flag ? "product/new_product" : "product/edit_product";
        }

        // id가 null이면 등록, 아니면 수정
        if (flag) {
            redirectAttributes.addFlashAttribute("success", "상품이 등록되었습니다.");
        }
        else {
            redirectAttributes.addFlashAttribute("success", "상품이 수정되었습니다.");
        }

        service.save(product);

        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(
            @PathVariable(name = "id") Long id,
            RedirectAttributes redirectAttributes
    ) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("success", "상품이 삭제되었습니다.");
        return "redirect:/products";
    }
}

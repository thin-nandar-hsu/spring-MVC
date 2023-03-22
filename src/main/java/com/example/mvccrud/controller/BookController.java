package com.example.mvccrud.controller;

import com.example.mvccrud.ds.Cart;
import com.example.mvccrud.entity.Author;
import com.example.mvccrud.entity.Book;
import com.example.mvccrud.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private Cart cart;


    @GetMapping("/cart/add-cart")
    public String addToCart(@RequestParam("id")int id){
        bookService.addToCart(id);
        return "redirect:/book/details?id="+id;

    }

    @ModelAttribute("cartSize")
    public int cartSize(){
        return bookService.cartSize();
    }


    @GetMapping("/book/details")
    public String detailsBook(@RequestParam("id")int id,Model model){
        model.addAttribute("book",bookService.findBookById(id));
        return "details-book";
    }


    @GetMapping("/book/update")
    public String updateForm(@RequestParam("id")int id,Model model){
        model.addAttribute("book",bookService.findBookById(id));
        this.bookId=id;
        model.addAttribute("authors",bookService.listAuthors());
        return "book-update";

    }

    int bookId;

    @PostMapping("/book/update")
    public String saveUpdateBook(@Valid Book book,BindingResult bindingResult,Model model,RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("book",bookService.findBookById(book.getId()));
            model.addAttribute("authors",bookService.listAuthors());
            return "book-update";
        }
        book.setId(bookId);
        bookService.update(book);
        redirectAttributes.addFlashAttribute("update",true);
        return "redirect:/list-books";
    }

    String imgUrl;
    Author author;
    int uiUpdateId;
    @GetMapping("/book/ui-update")
    public String uIUpdate(@RequestParam("id")int id,Model model) {
        Book updateBook = bookService.findBookById(id);
        imgUrl=updateBook.getImgUrl();
        this.author=updateBook.getAuthor();
        this.uiUpdateId=updateBook.getId();
        List<Book> bookList = bookService.listBooks()
                .stream()
                .map(b -> {
                    if(b.equals(updateBook)) {
                        b.setRender(true);
                    }
                    return b;
                }).collect(Collectors.toList());
        model.addAttribute("updateBook",updateBook);
        model.addAttribute("books", bookList);
        return "books";
    }

    @PostMapping("/book/ui/update")
    public String updateBookAgain(Book updateBook,BindingResult result){
        if(result.hasErrors()){
            return "redirect:/book/ui-update";
        }
        updateBook.setImgUrl(imgUrl);
        updateBook.setAuthor(author);
        updateBook.setId(uiUpdateId);

        updateBook.setRender(false);
        bookService.updateAgain(updateBook);
          return "redirect:/list-books";
    }

    @GetMapping("/book/remove")
     public String removeBook(@RequestParam("id")int id,RedirectAttributes redirectAttributes ){
         bookService.removeBook(id);
         redirectAttributes.addFlashAttribute("delete",true);
         return "redirect:/list-books";
     }

    @GetMapping({"/","/home"})
    public ModelAndView index(){
        return new ModelAndView("home","books",bookService.listBooks());


    }
    @GetMapping("/author-form")
    public String authorForm(Model model){
        model.addAttribute("author",new Author());
        return "author-form";
    }

    @PostMapping("/author-form")
    public String saveAuthor(@Valid Author author, BindingResult result){
        if(result.hasErrors()){
            return "author-form";
        }

        bookService.saveAuthor(author);

        return "redirect:/authors";
    }

    @GetMapping("/authors")
    public String listAuthor(Model model){
        model.addAttribute("authors",bookService.listAuthors());

        return "authors";
    }

    @GetMapping("/book-form")
    public String bookForm(Model model){
        model.addAttribute("authors",bookService.listAuthors());
        model.addAttribute("book",new Book());
        return "bookForm";
    }

    @PostMapping("/book-form")
    public String saveBook(@Valid Book book, BindingResult result, RedirectAttributes redirectAttributes,Model model){
        if(result.hasErrors()){
            model.addAttribute("authors",bookService.listAuthors());
            return "bookForm";
        }

        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/list-books";
    }

    @RequestMapping("/list-books")
    public String listAllBooks(Model model){
        model.addAttribute("delete",model.containsAttribute("delete"));
        model.addAttribute("success",model.containsAttribute("success"));
        model.addAttribute("update",model.containsAttribute("update"));
        model.addAttribute("books",bookService.listBooks());
        return "books";
    }

}

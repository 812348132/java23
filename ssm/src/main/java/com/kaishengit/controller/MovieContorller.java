package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Movie;
import com.kaishengit.service.MovieService;
import com.kaishengit.util.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by zjs on 2017/7/14.
 */
@Controller
@RequestMapping("/movie")
public class MovieContorller {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String findAll(@RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) String daoyan,
                          @RequestParam(required = false) Float min,
                          @RequestParam(required = false) Float max,
                          Model model){
        title = StringsUtil.isoToUtf8(title);
        daoyan = StringsUtil.isoToUtf8(daoyan);

        PageInfo<Movie> pageInfo = movieService.findByParam(title,daoyan,min,max,pageNo);
        model.addAttribute("page",pageInfo);
        model.addAttribute("title",title);
        model.addAttribute("daoyan",daoyan);
        model.addAttribute("min",min);
        model.addAttribute("max",max);
        return "movie/list";
    }

    /**
     * 添加新电影
     * @return
     */
    @GetMapping("/new")
    public String newMovie() {
        return "movie/new";
    }

    @PostMapping("/new")
    public String saveMovie(Movie movie, RedirectAttributes redirectAttributes) {
        movieService.save(movie);
        redirectAttributes.addFlashAttribute("message","添加成功");
        return "redirect:/movie";
    }

    @GetMapping("/del/{id:\\d+}")
    public String delMovie(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        movieService.delMovieById(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/movie";
    }

    @GetMapping("edit/{id:\\d+}")
    public String editMovie(@PathVariable Integer id,Model model){
        Movie movie = movieService.findById(id);
        model.addAttribute("movie",movie);
        return "movie/edit";
    }

    @PostMapping("edit/{id:\\d+}")
    public String editMovie(Movie movie,RedirectAttributes redirectAttributes){
        movieService.editMovie(movie);
        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/movie";
    }

}

package ru.yandex.practicum.catsgram.controller;

//import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;
import ru.yandex.practicum.catsgram.service.SortOrder;

//import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> findAll(
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "0") long from,
            @RequestParam(defaultValue = "10") int size) {
        SortOrder order = SortOrder.from(sort);
        if (order == null) {
            order = SortOrder.DESCENDING;
        }
        return postService.findAll(from, size, order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }

    @GetMapping("/{postId}")
    public Post findById(@PathVariable long postId) {
        return postService.findById(postId);
    }
}
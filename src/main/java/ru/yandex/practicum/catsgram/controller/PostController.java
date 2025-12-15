package ru.yandex.practicum.catsgram.controller;

//import lombok.RequiredArgsConstructor;
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
            order = SortOrder.DESCENDING;  // Дефолт, если неверный
        }
        return postService.findAll(from, size, order);
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }

    @GetMapping("/{postId}")  // Путь: /posts/{postId}, где {postId} — переменная
    public Post findById(@PathVariable long postId) {  // @PathVariable берёт postId из пути
        return postService.findById(postId);
    }
}
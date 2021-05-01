package com.example.foodkitchen.unit;

import com.example.foodkitchen.data.entities.ForumCategory;
import com.example.foodkitchen.data.entities.ForumTopic;
import com.example.foodkitchen.data.models.service.ForumCategoryServiceModel;
import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import com.example.foodkitchen.data.repositories.ForumCategoryRepository;
import com.example.foodkitchen.data.services.ForumCategoryService;
import com.example.foodkitchen.data.services.impl.ForumCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ForumCategoryServiceUnitTest {

    ForumCategoryService forumCategoryService;

    ForumCategoryRepository forumCategoryRepository;
    ModelMapper modelMapper;

    List<ForumCategory> categories;

    private static final String TOPIC_TITLE = "myTopicTitle";

    @BeforeEach
    public void init(){

        modelMapper = new ModelMapper();
        categories = new ArrayList<>();

        forumCategoryRepository = Mockito.mock(ForumCategoryRepository.class);
        Mockito.when(forumCategoryRepository.findAll()).thenReturn(categories);

        Mockito.when(forumCategoryRepository.saveAndFlush(Mockito.any(ForumCategory.class)))
                .thenAnswer(i -> {
                    categories.add(new ForumCategory());
                    return i.getArguments()[0];
                });

        forumCategoryService = new ForumCategoryServiceImpl(forumCategoryRepository, modelMapper);
    }

    @Test
    public void findAll_onInvoke_shouldReturnCategoriesWithTopicsSortedByDate(){

        categories.add(new ForumCategory(){{
            setTopics(Set.of(new ForumTopic(){{
                setTitle("topic1");
                setDate(new Date(){{
                    setTime(1234);
                }});
            }}, new ForumTopic(){{
                setTitle("topic2");
                setDate(new Date(){{
                    setTime(8888);
                }});
            }}, new ForumTopic(){{
                setTitle("topic3");
                setDate(new Date(){{
                    setTime(1111);
                }});
            }}));
        }});

        List<ForumCategoryServiceModel> allCategories = forumCategoryService.findAll();

        String[] topicNames = {"topic3", "topic1", "topic2"};
        AtomicInteger index = new AtomicInteger(0);

        Set<ForumTopicServiceModel> topics = allCategories
                .stream()
                .map(ForumCategoryServiceModel::getTopics)
                .collect(Collectors.toList())
                .get(0);

        topics.forEach(t -> {
            assertEquals(topicNames[index.get()], t.getTitle());
            index.set(index.get() + 1);
        });
    }

    @Test
    public void findByTitle_onNonExistingTitle_shouldReturnNul(){

        categories.add(new ForumCategory(TOPIC_TITLE));

        mockCategoryFindByTitle();

        assertNull(forumCategoryService.findByTitle("INVALID_TITLE"));
    }

    @Test
    public void findByTitle_onExistingTitle_shouldReturnIt(){

        categories.add(new ForumCategory(TOPIC_TITLE));

        mockCategoryFindByTitle();

        assertNotNull(forumCategoryService.findByTitle(TOPIC_TITLE));
    }

    @Test
    public void seedForumCategoriesInDB_onEmptyRepository_shouldSaveThem(){

        categories = new ArrayList<>();
        forumCategoryService.seedForumCategoriesInDB();

        assertEquals(5, categories.size());
    }

    @Test
    public void count_onEmptyRepository_shouldReturnZero(){

        categories = new ArrayList<>();
        assertEquals(0, forumCategoryService.count());
    }

    @Test
    public void count_onSomeCategories_shouldReturnTheirCount(){

        categories.add(new ForumCategory());
        categories.add(new ForumCategory());

        Mockito.when(forumCategoryRepository.count())
                .thenReturn(Long.valueOf(categories.size()));

        assertEquals(2, forumCategoryService.count());
    }

    private void mockCategoryFindByTitle() {
        Mockito.when(forumCategoryRepository.findByTitle(TOPIC_TITLE)).thenReturn(categories.stream()
                .filter(c -> c.getTitle().equals(TOPIC_TITLE))
                .findFirst()
                .orElse(null));
    }
}

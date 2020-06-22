package com.sarnava.newsapplication;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.sarnava.newsapplication.data.local.DBNews;
import com.sarnava.newsapplication.data.local.NewsDatabase;
import com.sarnava.newsapplication.data.network.ApiInterface;
import com.sarnava.newsapplication.ui.main.MainViewModel;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SampleTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    private ApiInterface apiInterface;

    //@Mock
    Application application;

    @Mock
    private Observer<List<DBNews>> observer;

    MainViewModel viewModel;
    private NewsDatabase newsDatabase;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);
        //Context context = mock(Context.class);
        application = mock(Application.class);
        viewModel = new MainViewModel(application);
        //newsDatabase = Room.databaseBuilder(context, NewsDatabase.class, "news_db").build();
        viewModel.getDbNews().observeForever(observer);
    }

    @Test
    public void test(){

        when(viewModel.getDbNews()).thenReturn(null);
        viewModel.getDbNews();

        assertTrue(viewModel.getDbNews().hasObservers());

        List<DBNews> list = new ArrayList<>();
        verify(observer).onChanged(list);
    }
}

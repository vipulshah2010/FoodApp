package foodapp.com.foodapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import foodapp.com.data.model.FoodItem;
import foodapp.com.data.repository.FoodItemsRepository;
import foodapp.com.data.repository.FoodItemsRepositoryImpl;
import foodapp.com.data.shared.ExecutionThread;
import foodapp.com.data.shared.PostExecutionThread;
import foodapp.com.data.store.local.FoodItemsLocalDataStore;
import foodapp.com.data.store.remote.FoodItemsCloudDataStore;
import foodapp.com.domain.fooditems.GetFoodItemsInteractor;
import foodapp.com.foodapp.foods.ui.FoodListContract;
import foodapp.com.foodapp.foods.ui.FoodListPresenterImpl;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

public class FoodListPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private
    ExecutionThread executionThread;

    @Mock
    private
    PostExecutionThread postExecutionThread;

    @Mock
    private
    FoodItemsLocalDataStore localDataStore;

    @Mock
    private
    FoodItemsCloudDataStore remoteDataStore;

    @Mock
    private
    FoodListContract.FoodListMVPView mvpView;

    private TestScheduler testScheduler;

    public FoodListPresenterTest() {
    }

    @Before
    public void setup() {
        testScheduler = new TestScheduler();
        Mockito.when(executionThread.getScheduler()).thenReturn(testScheduler);
        Mockito.when(postExecutionThread.getScheduler()).thenReturn(testScheduler);
    }

    @Test
    public void loadFoodItems() {

        List<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem());
        foodItems.add(new FoodItem());

        Mockito.when(localDataStore.getFoodItems()).thenReturn(Single.just(foodItems));
        Mockito.when(remoteDataStore.getFoodItems()).thenReturn(Single.just(foodItems));

        FoodItemsRepository foodItemsRepository = new FoodItemsRepositoryImpl(localDataStore, remoteDataStore);

        GetFoodItemsInteractor interactor = new GetFoodItemsInteractor(foodItemsRepository, executionThread, postExecutionThread);

        FoodListContract.FoodListPresenter<FoodListContract.FoodListMVPView> presenter = new FoodListPresenterImpl<>(interactor);
        presenter.onAttach(mvpView);

        presenter.loadFoodItems(false);
        Mockito.verify(mvpView).showLoading();
        testScheduler.triggerActions();
        Mockito.verify(mvpView).hideLoading();

        Mockito.verify(mvpView).onLoadFoodItems(foodItems);
    }
}
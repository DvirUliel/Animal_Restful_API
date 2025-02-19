package com.example.animal_restful_api.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.animal_restful_api.models.Animal;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private final MutableLiveData<List<Animal>> animalList = new MutableLiveData<>();
    private final MutableLiveData<String> lastAnimalInfo = new MutableLiveData<>();

    public MutableLiveData<List<Animal>> getAnimalList() {
        return animalList;
    }

    public MutableLiveData<String> getLastAnimalInfo() {
        return lastAnimalInfo;
    }

    public void setAnimalList(List<Animal> animals) {
        animalList.setValue(animals);
    }

    public void setLastAnimalInfo(String info) {
        lastAnimalInfo.setValue(info);
    }
}

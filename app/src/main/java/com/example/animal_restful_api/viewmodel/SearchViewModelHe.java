package com.example.animal_restful_api.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.animal_restful_api.models.AnimalHe;

import java.util.List;

public class SearchViewModelHe extends ViewModel {
    private final MutableLiveData<List<AnimalHe>> animalList = new MutableLiveData<>();
    private final MutableLiveData<String> lastAnimalInfo = new MutableLiveData<>();

    public MutableLiveData<List<AnimalHe>> getAnimalList() {
        return animalList;
    }

    public MutableLiveData<String> getLastAnimalInfo() {
        return lastAnimalInfo;
    }

    public void setAnimalList(List<AnimalHe> animals) {
        animalList.setValue(animals);
    }

    public void setLastAnimalInfo(String info) {
        lastAnimalInfo.setValue(info);
    }
}

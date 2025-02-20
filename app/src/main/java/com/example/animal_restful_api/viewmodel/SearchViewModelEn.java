package com.example.animal_restful_api.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.animal_restful_api.models.AnimalEn;
import java.util.List;

public class SearchViewModelEn extends ViewModel {
    private final MutableLiveData<List<AnimalEn>> animalList = new MutableLiveData<>();
    private final MutableLiveData<String> lastAnimalInfo = new MutableLiveData<>();

    public MutableLiveData<List<AnimalEn>> getAnimalList() {
        return animalList;
    }

    public MutableLiveData<String> getLastAnimalInfo() {
        return lastAnimalInfo;
    }

    public void setAnimalList(List<AnimalEn> animals) {
        animalList.setValue(animals);
    }

    public void setLastAnimalInfo(String info) {
        lastAnimalInfo.setValue(info);
    }
}

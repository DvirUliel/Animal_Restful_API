package com.example.animal_restful_api.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.animal_restful_api.models.AnimalHe;

import java.util.List;

public class SearchViewModelHe extends ViewModel {
    // MutableLiveData to hold the list of animals
    private final MutableLiveData<List<AnimalHe>> animalList = new MutableLiveData<>();

    // MutableLiveData to hold information about the last searched animal
    private final MutableLiveData<String> lastAnimalInfo = new MutableLiveData<>();

    /**
     * Getter for the animal list.
     * @return MutableLiveData containing the list of animals.
     */
    public MutableLiveData<List<AnimalHe>> getAnimalList() {
        return animalList;
    }

    /**
     * Getter for the last animal info.
     * @return MutableLiveData containing the information of the last searched animal.
     */
    public MutableLiveData<String> getLastAnimalInfo() {
        return lastAnimalInfo;
    }

    /**
     * Setter for the animal list.
     * @param animals List of animals to be set in the MutableLiveData.
     */
    public void setAnimalList(List<AnimalHe> animals) {
        animalList.setValue(animals);
    }

    /**
     * Setter for the last animal info.
     * @param info The information of the last searched animal to be set.
     */
    public void setLastAnimalInfo(String info) {
        lastAnimalInfo.setValue(info);
    }
}

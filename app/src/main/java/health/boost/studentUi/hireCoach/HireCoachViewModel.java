package health.boost.studentUi.hireCoach;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HireCoachViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HireCoachViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
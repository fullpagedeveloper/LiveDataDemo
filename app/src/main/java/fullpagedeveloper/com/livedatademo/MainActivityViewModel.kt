package fullpagedeveloper.com.livedatademo

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private lateinit var timer: CountDownTimer

    var timeValue = MutableLiveData<Long>()

    var finished = MutableLiveData<Boolean>()

    //data yang dapat berubah
    private val _seconds = MutableLiveData<Int>()
    fun seconds(): LiveData<Int>{
        return _seconds
    }


    fun startTimer(){
        timer = object: CountDownTimer(timeValue.value!!.toLong(), 1000){
            override fun onFinish() {
                finished.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished/1000 //merubah milidetik ke detik
                _seconds.value = timeLeft.toInt()
            }
        }.start()
    }

    fun stopTimer(){
        timer.cancel()
    }
}
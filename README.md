# LiveDataDemo

1. add dependency

        //Lifecycle Component
        implementation "android.arch.lifecycle:extensions:1.1.1"
        annotationProcessor "android.arch.lifecycle:compiler:1.1.1"

        //viewModel
        implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    
    

2. add new class MainActivityViewModel.kt dengan extend ViewModel()


          class MainActivityViewModel: ViewModel() {}
      
      
3. add variable dan data yang dapat berubah


      //variable global
      private lateinit var timer: CountDownTimer
      
      //data yang dapat berubah
      private val _seconds = MutableLiveData<Int>()

      
4. buat function dengan object


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
  
  5. selanjutnya tambahkan LiveData dari data yang dapat berubah kemudian di return
  
          
            //data yang dapat berubah
            private val _seconds = MutableLiveData<Int>()
            fun seconds(): LiveData<Int>{
                return _seconds
            }
            
            
6. selanjutnya buat variable lagi untuk menghentikan timer


              var timeValue = MutableLiveData<Long>()


7. Kemudian tambah function


              fun stopTimer(){
                      timer.cancel()
                  }
                  


8. sekarang buka MainActivity.kt
   dan implement object MainviewModel
   
   
           val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


9. selanjutnya ganggil object yang telah dibuat pada MainViewModel.kt



                    /**
                     * Menggunakan @lambda {it}
                     */
                    viewModel.seconds().observe(this@MainActivity, Observer {
                        text_number.text = it.toString()
                        Log.d("TAG", " $it")
                    })

                    /**
                     * Menggunakan @lambda {finish ->}
                     */
                    viewModel.finished.observe(this, Observer { finish ->
                        if (finish) {
                            Toast.makeText(this, "Finished!", Toast.LENGTH_SHORT).show()
                        }
                    })

                    button_Start.setOnClickListener {
                        if (editTextInputText.text.isEmpty() || editTextInputText.length() < 4){
                            Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.timeValue.value = editTextInputText.text.toString().toLong()
                            viewModel.startTimer()
                        }
                    }

                    button_Stop.setOnClickListener {
                        text_number.text = "0"
                        viewModel.stopTimer()
                    }
                }

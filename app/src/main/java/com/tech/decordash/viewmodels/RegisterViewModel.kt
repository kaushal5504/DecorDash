package com.tech.decordash.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.tech.decordash.data.User
import com.tech.decordash.util.Constants.USER_COLLECTION
import com.tech.decordash.util.RegisterFieldState
import com.tech.decordash.util.RegisterValidation
import com.tech.decordash.util.Resource
import com.tech.decordash.util.ValidationCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth ,
    private val db : FirebaseFirestore
) :ViewModel(),LifecycleObserver
{
    val validate = ValidationCheck()

    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register : MutableStateFlow<Resource<User>> = _register

    private val _validation = Channel<RegisterFieldState>()
    val validation= _validation.receiveAsFlow()
    fun createAccountWithEmailAndPasword(user : User, password :String)
    {
        if(checkValidation(user, password))
        {
            runBlocking {
                _register.emit(Resource.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email , password)
                .addOnSuccessListener {
                    it.user?.let {
                        saveUserInfo(it.uid,user)
                       // _register.value = Resource.Success(it)
                    }

                }
                .addOnFailureListener{
                    _register.value = Resource.Error(it.message.toString())

                }


        }
        else
        {
            val registerFieldState = RegisterFieldState(
                validate.validateEmail(user.email),validate.validatePassword(password)

            )
            runBlocking {
                _validation.send(registerFieldState)
            }

        }


    }

    private fun saveUserInfo(userUid :String  ,user: User) {
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)

            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())

            }



    }

    private fun checkValidation(user: User, password: String) :Boolean {
        val emailValidation = validate.validateEmail(user.email)
        val passwordValidation = validate.validatePassword(password)

        val shouldRegister = emailValidation is RegisterValidation.Success
                && passwordValidation is RegisterValidation.Success

        return shouldRegister
    }
}




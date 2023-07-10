package com.tech.decordash.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.tech.decordash.R
import com.tech.decordash.data.User
import com.tech.decordash.databinding.FragmentRegisterBinding
import com.tech.decordash.util.RegisterValidation
import com.tech.decordash.util.Resource
import com.tech.decordash.viewmodels.RegisterViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val TAG = "RegisterFragment"
@AndroidEntryPoint

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding : FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            buttonRegisterRegister.setOnClickListener{
                val user = User(
                    edFirstNameRegister.text.toString().trim(),
                    edLastNameRegister.text.toString().trim(),
                    edEmailRegister.text.toString().trim()


                )
                val password = edPasswordRegister.text.toString()

                viewModel.createAccountWithEmailAndPasword(user,password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.register.collect{
                when(it)
                {
                    is Resource.Loading->
                    {
                        binding.buttonRegisterRegister.startAnimation()

                    }
                    is Resource.Success->
                    {
                        Log.d("test" ,it.data.toString())
                        binding.buttonRegisterRegister.revertAnimation()

                    }
                    is Resource.Error->
                    {
                        Log.e(TAG , it.message.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }
                    else -> Unit

                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect{ validation->
                if(validation.email is RegisterValidation.Failure){
                    withContext(Dispatchers.Main){
                        binding.edEmailRegister.apply {
                            requestFocus()
                            error  = validation.email.message
                        }
                    }

                }

                if(validation.password is RegisterValidation.Failure){
                    withContext(Dispatchers.Main){
                        binding.edPasswordRegister.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }

            }
        }
    }


}
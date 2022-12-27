package com.example.mviexample

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class Intention

private object SomeIntentA : Intention()
private object SomeIntentB : Intention()

class MyViewModel : ViewModel() {

    private val state = BroadcastChannel<MyState>(Channel.UNLIMITED)

//    private val state: MutableStateFlow<MyState> = MutableStateFlow(
//        MyState(
//            isShowProgress = true,
//            data = listOf("Hello, some data")
//        )
//    )


//    private val state: Flow<MyState> = flow {
//        emit(
//            MyState(
//                isShowProgress = true,
//                data = listOf("Hello, some data")
//            )
//        )
//    }

    fun reduce(intent: Intention, oldState: MyState): MyState {
        return when (intent) {
            is SomeIntentA -> {
                state.send(oldState.copy(isShowProgress = true))
                state.tryEmit(oldState.copy(isShowProgress = true))
            }
            is SomeIntentB -> {
                state.tryEmit(oldState.copy(isShowProgress = false))
            }
            else -> {
                state.tryEmit(oldState.copy(isShowProgress = false))
            }
        }
    }
}


abstract class Reducer<S : UiState, E : UiEvent>(initialVal: S) {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
    val state: StateFlow<S>
        get() = _state

    val timeCapsule: TimeCapsule<S> = TimeTravelCapsule { storedState ->
        _state.tryEmit(storedState)
    }

    init {
        timeCapsule.addState(initialVal)
    }

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    fun setState(newState: S) {
        val success = _state.tryEmit(newState)

        if (BuildConfig.DEBUG && success) {
            timeCapsule.addState(newState)
        }
    }

    abstract fun reduce(oldState: S, event: E)
}

interface UiState

interface UiEvent


data class MyState(
    val isShowProgress: Boolean = false,
    val error: Throwable? = null,
    val data: List<String>
)


@Immutable
sealed class MainScreenUiEvent : UiEvent {
    data class ShowData(val items: List<MainScreenItem>) : MainScreenUiEvent()
    data class OnChangeDialogState(val show: Boolean) : MainScreenUiEvent()
    data class AddNewItem(val text: String) : MainScreenUiEvent()
    data class OnItemCheckedChanged(val index: Int, val isChecked: Boolean) : MainScreenUiEvent()
    object DismissDialog : MainScreenUiEvent()
}

@Immutable
data class MainScreenState(
    val isLoading: Boolean,
    val data: List<MainScreenItem>,
    val isShowAddDialog: Boolean,
) : UiState {
    companion object {
        fun initial() = MainScreenState(
            isLoading = true,
            data = emptyList(),
            isShowAddDialog = false
        )
    }
    override fun toString(): String {
        return "isLoading: $isLoading, data.size: ${data.size}, isShowAddDialog: $isShowAddDialog"
    }
}

sealed class MainScreenItem {
    object MainScreenAddButtonItem : MainScreenItem()
    data class MainScreenTodoItem(
        val isChecked: Boolean,
        val text: String,
    ) : MainScreenItem()
}


//class MainViewModel<MainScreenState, MainScreenUiEvent> (
class MainViewModel (
//    private val getTodos: IGetTodosUseCase,
    private val dispatcher: CoroutineDispatcher,
//    private val viewMapper: MainScreenViewDataMapper,
): ViewModel() {

    private val reducer = MainReducer(MainScreenState.initial())

    val state: StateFlow<MainScreenState>
        get() = reducer.state

    val timeMachine: TimeCapsule<MainScreenState>
        get() = reducer.timeCapsule

    init {
        viewModelScope.launch(dispatcher) {
//            val data = getTodos.invoke()
//            sendEvent(MainScreenUiEvent.ShowData(viewMapper.buildScreen(data)))
        }
    }

    private fun sendEvent(event: MainScreenUiEvent) {
        reducer.sendEvent(event)
    }

    fun changeAddDialogState(show: Boolean) {
        sendEvent(MainScreenUiEvent.OnChangeDialogState(show))
    }

    fun addNewItem(text: String) {
        sendEvent(MainScreenUiEvent.AddNewItem(text))
    }

    fun onItemCheckedChanged(index: Int, isChecked: Boolean) {
        sendEvent(MainScreenUiEvent.OnItemCheckedChanged(index, isChecked))
    }

    private class MainReducer(initial: MainScreenState) : Reducer<MainScreenState, MainScreenUiEvent>(initial) {
        override fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {
            when (event) {
                is MainScreenUiEvent.OnChangeDialogState -> {
                    setState(oldState.copy(isShowAddDialog = event.show))
                }
                is MainScreenUiEvent.ShowData -> {
                    setState(oldState.copy(isLoading = false, data = event.items))
                }
                is MainScreenUiEvent.DismissDialog -> {
                    setState(oldState.copy(isShowAddDialog = false))
                }
                is MainScreenUiEvent.AddNewItem -> {
                    val newList = oldState.data.toMutableList()
                    newList.add(
                        index = oldState.data.size - 1,
                        element = MainScreenItem.MainScreenTodoItem(false, event.text),
                    )
                    setState(oldState.copy(
                        data = newList,
                        isShowAddDialog = false
                    ))
                }
                is MainScreenUiEvent.OnItemCheckedChanged -> {
                    val newList = oldState.data.toMutableList()
                    newList[event.index] = (newList[event.index] as MainScreenItem.MainScreenTodoItem).copy(isChecked = event.isChecked)
                    setState(oldState.copy(data = newList))
                }
            }
        }
    }
}




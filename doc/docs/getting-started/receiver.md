# Receiver

In order to listen for incoming changes from your Live set, two implementations are available:

- ReactiveReceiver
- CallbackReceiver


## ReactiveReceiver

[ReactiveReceiver](../api-reference/fr.arthurvimond.oscletonsdk/-reactive-receiver/)
provides RxJava Observables to subscribe to, emitting each Live set change independently.
Using RxJava Observables, you can create as many observers as you want.


## CallbackReceiver

[CallbackReceiver](../api-reference/fr.arthurvimond.oscletonsdk/-callback-receiver/)
provides listeners to be set independently, triggered for each Live set change.
Note: Only one listener can be set for a specific Live set change.
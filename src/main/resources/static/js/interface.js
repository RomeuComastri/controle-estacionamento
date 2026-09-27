(() => {
    const dialog = document.getElementById('delete-dialog');
    if (!dialog || typeof dialog.showModal !== 'function') return;
    const cancel = document.getElementById('delete-cancel');
    const confirmButton = document.getElementById('delete-confirm');
    let pendingForm = null;
    let confirmedForm = null;

    window.confirmDeletion = (event) => {
        const form = event.target;
        if (confirmedForm === form) {
            confirmedForm = null;
            return true;
        }
        event.preventDefault();
        pendingForm = form;
        const kind = form.dataset.deleteKind;
        document.getElementById('delete-title').textContent = `Excluir ${kind}?`;
        document.getElementById('delete-description').textContent = kind === 'cliente'
            ? 'A exclusão é permanente. Clientes com veículos vinculados serão preservados. Deseja continuar?'
            : 'A exclusão deste veículo é permanente. O cadastro do proprietário será mantido. Deseja continuar?';
        confirmButton.textContent = `Excluir ${kind}`;
        confirmButton.disabled = false;
        dialog.showModal();
        cancel.focus();
        return false;
    };
    cancel.addEventListener('click', () => dialog.close());
    dialog.addEventListener('close', () => { pendingForm = null; });
    confirmButton.addEventListener('click', () => {
        const form = pendingForm;
        if (!form) return;
        confirmButton.disabled = true;
        confirmedForm = form;
        dialog.close();
        form.requestSubmit();
    });
})();

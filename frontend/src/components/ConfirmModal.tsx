interface ConfirmModalProps {
    message: string; // Message to show
    onConfirm: () => void; // Function to run if confirmed
    onCancel: () => void; // Function to run if canceled
    isOpen: boolean; // Control modal visibility
  }
  
  export default function ConfirmModal({ message, onConfirm, onCancel, isOpen }: ConfirmModalProps) {
    if (!isOpen) return null;
  
    return (
      <div className="modal-overlay">
        <div className="modal-content">
          <p>{message}</p>
          <div className="modal-actions">
            <button className="cancel-btn" onClick={onCancel}>Cancel</button>
            <button className="confirm-btn" onClick={onConfirm}>Delete</button>
          </div>
        </div>
      </div>
    );
  }
  
interface ToastProps {
    message: string;
    show: boolean;
  }
  
  export default function Toast({ message, show }: ToastProps) {
    if (!show) return null;
  
    return (
      <div className="toast">
        ✅ {message}
      </div>
    );
  }
  
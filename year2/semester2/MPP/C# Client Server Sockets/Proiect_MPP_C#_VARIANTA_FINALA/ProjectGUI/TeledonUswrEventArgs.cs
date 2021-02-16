using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectGUI
{
    public enum TeledonUserEvent
    {
        DONATION_MADE, COMMIT_DONATORI
    };
    public class TeledonUswrEventArgs : EventArgs
    {
        private readonly TeledonUserEvent userEvent;
        private readonly Object data;

        public TeledonUswrEventArgs(TeledonUserEvent userEvent, object data)
        {
            this.userEvent = userEvent;
            this.data = data;
        }

        public TeledonUserEvent UserEventType
        {
            get { return userEvent; }
        }

        public object Data { get { return data; } }
    }
}

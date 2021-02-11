using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace servicess
{
    public class TeledonException : Exception
    {
        public TeledonException():base() { }
        public TeledonException(string msg) : base(msg) { }
    }
}

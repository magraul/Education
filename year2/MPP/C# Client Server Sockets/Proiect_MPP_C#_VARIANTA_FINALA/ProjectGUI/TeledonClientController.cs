using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DomainRepoLibrary.entities;
using servicess;
using System.Text.RegularExpressions;
using Domain_Repo.entities;

namespace ProjectGUI
{
    public class TeledonClientController : ITeledonObserver
    {
        private Donator donatorSelectat = null;
        public event EventHandler<TeledonUswrEventArgs> updateEvent;
        private readonly ITeledonServices server;
        private Angajat curentUser;

        public TeledonClientController(ITeledonServices server)
        {
            this.server = server;
            curentUser = null;
        }

        public void login(string username, string parola)
        {
            Angajat angajat = new Angajat(username, "", "", username, parola);
            server.login(angajat, this);
            server.check(angajat);
            curentUser = angajat;
        }

        public void commitDonatori(List<string> donatori)
        {
            TeledonUswrEventArgs userArgs = new TeledonUswrEventArgs(TeledonUserEvent.COMMIT_DONATORI, donatori);
            OnUserEvent(userArgs);
        }

        protected virtual void OnUserEvent(TeledonUswrEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
        }

        internal List<CazDTO> getCazuriDTO()
        {
            return server.getCauriDTO();
        }

        public void donationMade(List<CazDTO> cazuri)
        {
            TeledonUswrEventArgs userArgs = new TeledonUswrEventArgs(TeledonUserEvent.DONATION_MADE, cazuri);
            OnUserEvent(userArgs);
        }

        public void logout()
        {
            server.logout(curentUser, this);
            curentUser = null;
        }

        public List<string> getNumeDonators()
        {
            return server.getDonators();
        }

        internal int getCazId(CazDTO cazDTO)
        {
            return server.getCazId(cazDTO);
        }

        internal void donatie(int idCaz, string text1, string text2, string text3, float v)
        {
            server.commitDonatori(text1, text3, text2);
            if (donatorSelectat == null)
            {
                donatorSelectat = server.getDonator(text1);
            }
            server.donatieS(v, donatorSelectat.Id, idCaz);
            donatorSelectat = null;


        }

        internal void setDonator(string text)
        {
            donatorSelectat = server.getDonator(text);
        }
    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DomainRepoLibrary.entities;

namespace ProjectGUI
{
    using TeledonExeption = servicess.TeledonException;
    public partial class TeledonView : Form
    {
        private readonly TeledonClientController ctrl;
        private readonly List<string> donatori;
        
        private readonly List<CazDTO> cazuri;
        //service.Service Service = new service.Service(new Domain_Repo.repositories.AngajatiDBRepository(), new Domain_Repo.repositories.CazuriDBRepository(), new Domain_Repo.repositories.DonatiiDBRepository(), new Domain_Repo.repositories.DonatoriDBRepository(), new DomainRepoLibrary.validators.ValidatorCazuri(), new DomainRepoLibrary.validators.ValidatorDonatii(), new DomainRepoLibrary.validators.ValidatorDonator());
        DataTable table = new DataTable();
        public TeledonView(TeledonClientController ctrl)
        { 
           
            InitializeComponent();
            this.ctrl = ctrl;
            donatori = ctrl.getNumeDonators();
            cazuri = ctrl.getCazuriDTO();

            sincronizeazTabel(cazuri);
            listBox1.DataSource = donatori;
            ctrl.updateEvent += userUpdate;
        }

        private void Teledon_FormClosing(object sender, FormClosingEventArgs e)
        {
            if(e.CloseReason == CloseReason.UserClosing)
            {
                ctrl.logout();
                ctrl.updateEvent -= userUpdate;
                Application.Exit();
            }
        }

        public void userUpdate(object sender, TeledonUswrEventArgs e)
        {
            if(e.UserEventType == TeledonUserEvent.COMMIT_DONATORI)
            {
                List<string> d = (List<string>)e.Data;
                donatori.Clear();
                d.ForEach(x => donatori.Add(x));
                listBox1.BeginInvoke(new UpdateListBoxCallback(this.updateList), new Object[] { listBox1, donatori });
            }

            if(e.UserEventType == TeledonUserEvent.DONATION_MADE)
            {
                List<CazDTO> d = (List<CazDTO>)e.Data;
                cazuri.Clear();
                d.ForEach(x => cazuri.Add(x));
                dataGridView1.BeginInvoke(new UpdateTableCallBack(this.updateTable), new Object[] { dataGridView1, cazuri });
                //sincronizeazTabel(cazuri);

            }
        }

        private void updateTable(DataGridView dataGrid, List<CazDTO> cazuri)
        {
            table.Rows.Clear();
            table.Columns.Clear();

            table.Columns.Add("Descriere", typeof(string));
            table.Columns.Add("Suma Adunata", typeof(string));

            dataGrid.DataSource = table;

            dataGridView1.Columns[0].HeaderCell.Style.Font = new Font("Tahoma", 10, FontStyle.Bold);
            dataGridView1.Columns[1].HeaderCell.Style.Font = new Font("Tahoma", 10, FontStyle.Bold);

            UpdateFont();

            foreach (CazDTO caz in cazuri)
            {
                table.Rows.Add(caz.Descriere, caz.SumaAdunata);
            }


        }

        private void updateList(ListBox listBox, List<string> newData)
        {
            listBox.DataSource = null;
            listBox.DataSource = newData;
        }

        public delegate void UpdateListBoxCallback(ListBox list, List<string> data);

        public delegate void UpdateTableCallBack(DataGridView dataGrid, List<CazDTO> c);
        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void UpdateFont()
        {
            //Change cell font
            foreach (DataGridViewColumn c in dataGridView1.Columns)
            {
                c.DefaultCellStyle.Font = new Font("Arial", 13.5F, GraphicsUnit.Pixel);
            }
        }

        private void sincronizeazTabel(List<CazDTO> cazuri)
        {
            table.Rows.Clear();
            table.Columns.Clear();

            table.Columns.Add("Descriere", typeof(string));
            table.Columns.Add("Suma Adunata", typeof(string));


            dataGridView1.DataSource = table;
            dataGridView1.Columns[0].HeaderCell.Style.Font = new Font("Tahoma", 10, FontStyle.Bold);
            dataGridView1.Columns[1].HeaderCell.Style.Font = new Font("Tahoma", 10, FontStyle.Bold);

            UpdateFont();

            foreach (CazDTO caz in cazuri)
            {
                table.Rows.Add(caz.Descriere, caz.SumaAdunata);
            }

            /*textBox1.Text = "";
            textBox2.Text = "";
            textBox3.Text = "";
            textBox4.Text = "";
            textBox5.Text = "";
           */

        }


        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            string linie = listBox1.GetItemText(listBox1.SelectedItem);
            if (linie != "")
            {
                string[] elems = linie.Split(new string[] { "  " }, StringSplitOptions.None);
                textBox4.Text = elems[1];
                textBox3.Text = elems[2];
                textBox5.Text = elems[0];
                ctrl.setDonator(textBox4.Text);
            }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            listBox1.DataSource = donatori.Where(x => x.Contains(textBox1.Text)).ToList();
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (dataGridView1.Rows[e.RowIndex].Cells[e.ColumnIndex].Value != null)
            {
                dataGridView1.CurrentRow.Selected = true;
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            //donatie
            if (dataGridView1.SelectedRows.Count == 0)
                MessageBox.Show("Nu ati selectat un caz caritabil!");
            else
            {
                try
                {
                    int idCaz = ctrl.getCazId(new CazDTO(dataGridView1.SelectedRows[0].Cells["Descriere"].FormattedValue.ToString(), dataGridView1.SelectedRows[0].Cells["Suma Adunata"].FormattedValue.ToString()));
                    ctrl.donatie(idCaz, textBox5.Text, textBox4.Text, textBox3.Text, float.Parse(textBox2.Text));
                   
                } catch(TeledonExeption ee)
                {
                    MessageBox.Show(ee.Message);
                }
                }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            ctrl.logout();
            ctrl.updateEvent -= userUpdate;
            Application.Exit();
        }

        private void TeledonView_Load(object sender, EventArgs e)
        {

        }

    }
}

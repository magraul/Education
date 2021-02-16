using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ProjectGUI
{
    public partial class LogInView : Form
    {
       
        private TeledonClientController ctrl;
        public LogInView(TeledonClientController ctrl)
        {
            
            
            InitializeComponent();
            this.ctrl = ctrl;
            textBox1.Font = new Font(textBox1.Font.FontFamily, 12);
            textBox2.Font = new Font(textBox2.Font.FontFamily, 12);
        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            //login
            if (textBox1.Text == "" || textBox2.Text == "")
                MessageBox.Show("Nu ati introdus toate datele!");
            else
            {
                try
                {
                    ctrl.login(textBox1.Text, textBox2.Text);
                    
                    TeledonView teledon = new TeledonView(ctrl);
                    teledon.Text = "View pentru " + textBox1.Text;
                    teledon.Show();
                    this.Hide();

                } catch(Exception ex)
                {
                    MessageBox.Show("Login error" + ex.Message);
                    return;
                }
              
            }
        }
    }
}

namespace java teledon
namespace csharp teledon



struct CazDTO {
    1: string descriere
    2: double suma
}


service TeledonService
{
    bool check(1: string userName, 2: string password, 3: i32 port)
    void donatieS(1: CazDTO caz, 2: string numeDonator, 3: string adresa, 4: string nrTel, 5: double sumaDonata)
    list<string> getDonators()
    list<CazDTO> getCauriDTO()
}
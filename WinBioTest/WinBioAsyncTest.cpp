#include <windows.h>
#include <winbio.h>
#include <iostream>
#include <conio.h>

#pragma comment(lib, "winbio.lib")

void CALLBACK BiometricCallback(PWINBIO_ASYNC_RESULT result)
{
    std::cout << "\n\n=== CALLBACK RECEIVED ===\n";

    if (result == nullptr)
    {
        std::cout << "Result is NULL.\n";
        return;
    }

    std::cout << "Operation status: 0x"
              << std::hex << result->ApiStatus
              << std::dec << "\n";

    std::cout << "Operation type: "
              << result->Operation
              << "\n";

    std::cout << "Unit ID: "
              << result->UnitId
              << "\n";

    if (SUCCEEDED(result->ApiStatus))
    {
        std::cout << "Fingerprint identification completed!\n";

        std::cout << "Identity type: "
                  << result->Parameters.Identify.Identity.Type
                  << "\n";

        if (result->Parameters.Identify.Identity.Type == WINBIO_ID_TYPE_SID)
        {
            std::cout << "A Windows user SID was returned.\n";
        }
        else if (result->Parameters.Identify.Identity.Type == WINBIO_ID_TYPE_GUID)
        {
            std::cout << "A GUID identity was returned.\n";
        }
    }
    else
    {
        std::cout << "Fingerprint identification failed.\n";

        std::cout << "Reject detail: "
                  << result->Parameters.Identify.RejectDetail
                  << "\n";
    }

    WinBioFree(result);
}

int main()
{
    std::cout << "=== SmartAttend WinBio Async Test ===\n\n";

    WINBIO_SESSION_HANDLE session = NULL;

    std::cout << "Opening Windows Biometric session...\n";

    HRESULT hr = WinBioAsyncOpenSession(
        WINBIO_TYPE_FINGERPRINT,
        WINBIO_POOL_SYSTEM,
        WINBIO_FLAG_DEFAULT,
        NULL,
        0,
        NULL,
        WINBIO_ASYNC_NOTIFY_CALLBACK,
        NULL,
        0,
        BiometricCallback,
        NULL,
        FALSE,
        &session
    );

    if (FAILED(hr))
    {
        std::cout << "WinBioAsyncOpenSession FAILED.\n";

        std::cout << "HRESULT: 0x"
                  << std::hex << hr
                  << std::dec << "\n";

        return 1;
    }

    std::cout << "Session opened successfully.\n\n";

    std::cout << "Place your enrolled finger on the sensor...\n";

    hr = WinBioIdentify(
        session,
        NULL,
        NULL,
        NULL,
        NULL
    );

    std::cout << "\nWinBioIdentify returned: 0x"
              << std::hex << hr
              << std::dec << "\n";

    if (FAILED(hr))
    {
        std::cout << "WinBioIdentify could not start.\n";

        WinBioCloseSession(session);

        return 1;
    }

    std::cout << "Identification request started.\n";
    std::cout << "Waiting for the fingerprint callback...\n";
    std::cout << "\nPress any key to exit.\n";

    _getch();

    WinBioCloseSession(session);

    std::cout << "\nSession closed.\n";

    return 0;
}
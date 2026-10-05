#include <windows.h>
#include <stdio.h>
#include <sddl.h>
#include <winbio.h>

#pragma comment(lib, "winbio.lib")
#pragma comment(lib, "advapi32.lib")

int main()
{
    HANDLE tokenHandle = NULL;
    DWORD tokenSize = 0;
    PTOKEN_USER tokenUser = NULL;

    WINBIO_IDENTITY identity = {};
    WINBIO_BIOMETRIC_TYPE factors = WINBIO_NO_TYPE_AVAILABLE;

    printf("Checking Windows biometric enrollment...\n\n");

    // Open the current user's access token
    if (!OpenProcessToken(
        GetCurrentProcess(),
        TOKEN_QUERY,
        &tokenHandle))
    {
        printf("OpenProcessToken FAILED: %lu\n", GetLastError());
        return 1;
    }

    // Find required buffer size
    GetTokenInformation(
        tokenHandle,
        TokenUser,
        NULL,
        0,
        &tokenSize
    );

    tokenUser = (PTOKEN_USER)malloc(tokenSize);

    if (tokenUser == NULL)
    {
        printf("Memory allocation failed.\n");
        CloseHandle(tokenHandle);
        return 1;
    }

    // Get the current user's SID
    if (!GetTokenInformation(
        tokenHandle,
        TokenUser,
        tokenUser,
        tokenSize,
        &tokenSize))
    {
        printf("GetTokenInformation FAILED: %lu\n", GetLastError());
        free(tokenUser);
        CloseHandle(tokenHandle);
        return 1;
    }

    // Put the SID into WINBIO_IDENTITY
    identity.Type = WINBIO_ID_TYPE_SID;

    DWORD sidLength = GetLengthSid(tokenUser->User.Sid);

    if (sidLength > SECURITY_MAX_SID_SIZE)
    {
        printf("SID is too large.\n");
        free(tokenUser);
        CloseHandle(tokenHandle);
        return 1;
    }

    CopySid(
        SECURITY_MAX_SID_SIZE,
        identity.Value.AccountSid.Data,
        tokenUser->User.Sid
    );

    identity.Value.AccountSid.Size = sidLength;

    printf("Current Windows user SID obtained successfully.\n");
    printf("Checking enrolled biometric factors...\n\n");

    HRESULT hr = WinBioGetEnrolledFactors(
        &identity,
        &factors
    );

    printf("WinBioGetEnrolledFactors returned.\n");
    printf("HRESULT: 0x%08X\n", hr);
    printf("Factors: 0x%08X\n\n", factors);

    if (SUCCEEDED(hr))
    {
        if (factors & WINBIO_TYPE_FINGERPRINT)
        {
            printf("FINGERPRINT: ENROLLED\n");
        }
        else
        {
            printf("FINGERPRINT: NOT ENROLLED\n");
        }

        if (factors & WINBIO_TYPE_FACIAL_FEATURES)
        {
            printf("FACE: ENROLLED\n");
        }

        if (factors == WINBIO_NO_TYPE_AVAILABLE)
        {
            printf("No biometric factors reported.\n");
        }
    }
    else
    {
        printf("WinBioGetEnrolledFactors FAILED.\n");
    }

    free(tokenUser);
    CloseHandle(tokenHandle);

    printf("\nDone.\n");

    return 0;
}
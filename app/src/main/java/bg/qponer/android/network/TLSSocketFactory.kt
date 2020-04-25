package bg.qponer.android.network

import android.content.Context
import java.net.InetAddress
import java.net.Socket
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.*

class TLSSocketFactory private constructor(
    private val delegate: SSLSocketFactory,
    internal val trustManagers: Array<TrustManager>
) : SSLSocketFactory() {

    override fun getDefaultCipherSuites(): Array<String> =
        delegate.defaultCipherSuites

    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket =
        delegate.createSocket(s, host, port, autoClose)

    override fun createSocket(host: String?, port: Int): Socket =
        delegate.createSocket(host, port)

    override fun createSocket(
        host: String?,
        port: Int,
        localHost: InetAddress?,
        localPort: Int
    ): Socket =
        createSocket(host, port, localHost, localPort)

    override fun createSocket(host: InetAddress?, port: Int): Socket =
        delegate.createSocket(host, port)

    override fun createSocket(
        address: InetAddress?,
        port: Int,
        localAddress: InetAddress?,
        localPort: Int
    ): Socket =
        delegate.createSocket(address, port, localAddress, localPort)

    override fun getSupportedCipherSuites(): Array<String> =
        delegate.supportedCipherSuites

    companion object {

        fun create(context: Context, trustedCertificates: List<Int>): TLSSocketFactory {
            val (keyManagers, trustManagers) = createManagersForCertificates(
                trustedCertificates,
                context
            )

            val delegate = createDelegateFactory(keyManagers, trustManagers)

            return TLSSocketFactory(delegate, trustManagers)
        }

        private fun createDelegateFactory(
            keyManagers: Array<KeyManager>,
            trustManagers: Array<TrustManager>
        ): SSLSocketFactory =
            SSLContext.getInstance("TLS")
                .apply { init(keyManagers, trustManagers, null) }.socketFactory

        private fun createManagersForCertificates(
            trustedCertificates: List<Int>,
            context: Context
        ): Pair<Array<KeyManager>, Array<TrustManager>> {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = createKeyStore()

            trustedCertificates
                .withIndex()
                .associate { indexedCertificate ->
                    indexedCertificate.index to context.resources.openRawResource(
                        indexedCertificate.value
                    ).use { certificateFactory.generateCertificate(it) }
                }
                .entries
                .forEach { keyStore.setCertificateEntry(it.key.toString(), it.value) }

            val keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
                    .apply { init(keyStore, null) }
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                    .apply { init(keyStore) }
            return keyManagerFactory.keyManagers to trustManagerFactory.trustManagers
        }

        private fun createKeyStore() =
            KeyStore.getInstance(KeyStore.getDefaultType())
                .apply { load(null, null) }
    }
}